package com.hit.racemanagement.data.repositories

import com.hit.racemanagement.data.model.Racer
import kotlinx.coroutines.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.js.Promise
import kotlin.js.JsAny
import kotlin.js.JsString


@OptIn(ExperimentalWasmJsInterop::class)
class WebAuthRepository : AuthRepository {

    // 1. Config JSON Formatter (Opsional tapi bagus untuk konsistensi)
    private val jsonFormatter = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
    }

    override suspend fun registerRacer(password: String, racer: Racer): Result<String> {
        return try {
            val email = racer.email

            // 1. Panggil JS (Return Promise<JsString>)
            // Kita pakai .await() lalu convert ke String Kotlin
            val uidJs = createUserJs(email, password).await<JsString>()
            val uid = uidJs.toString() // Convert JsString ke Kotlin String
            println("uid: $uid")

            // 2. Simpan ke Firestore
            val newRacer = racer.copy(id = uid)

            // Encode ke String JSON (Kotlin)
            val jsonString = jsonFormatter.encodeToString(newRacer)

            println("Mengirim data ke Firestore untuk UID: $uid")
            println("Payload JSON: $jsonString")

            println("coba save ke Firestore...")

            saveRacerFirestoreJs(uid, jsonString).await<JsAny?>()

            println("✅ Register Sukses untuk UID: $uid")
            Result.success(uid)
        } catch (e: Exception) {
            println("Error Register: ${e.message}") // Ganti console.error dengan println
            Result.failure(Exception(e.message))
        }
    }

    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            val uidJs = signInJs(email, password).await<JsString>()
            Result.success(uidJs.toString())
        } catch (e: Exception) {
            println("Error Login: ${e.message}")
            Result.failure(Exception(e.message))
        }
    }

    override suspend fun getUserRole(userId: String): Result<String> {
        return try {
            val roleJs = getRoleJs(userId).await<JsString>()
            Result.success(roleJs.toString())
        } catch (e: Exception) {
            Result.failure(Exception("Gagal ambil role"))
        }
    }
}

// --- FUNGSI INTEROP (WASM STRICT TYPES) ---

// Return tipe harus Promise<JsString> (bukan String biasa)
@OptIn(ExperimentalWasmJsInterop::class)
fun createUserJs(email: String, password: String): Promise<JsString> = js("""
    {
        return firebase.auth().createUserWithEmailAndPassword(email, password)
            .then(credential => credential.user.uid);
    }
""")

@OptIn(ExperimentalWasmJsInterop::class)
fun signInJs(email: String, password: String): Promise<JsString> = js("""
    {
        return firebase.auth().signInWithEmailAndPassword(email, password)
            .then(credential => credential.user.uid);
    }
""")

// JsAny digunakan untuk data object
@OptIn(ExperimentalWasmJsInterop::class)
fun saveRacerFirestoreJs(uid: String, jsonString: String): Promise<JsAny?> = js("""
    {
        // 1. UBAH STRING JADI OBJECT JS MURNI
        var dataObject = JSON.parse(jsonString);
   
        // 2. KIRIM OBJECT (BUKAN STRING) KE FIRESTORE
        return firebase.firestore().collection("racers").doc(uid).set(dataObject);
    }
""")

@OptIn(ExperimentalWasmJsInterop::class)
fun getRoleJs(uid: String): Promise<JsString> = js("""
    {
        return firebase.firestore().collection("racers").doc(uid).get()
            .then(doc => {
                if (doc.exists) {
                    return doc.data().role || "racer";
                } else {
                    throw new Error("User not found");
                }
            });
    }
""")
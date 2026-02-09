package com.hit.racemanagement.ui.features.leaderboard.component

data class RacerResultDummy(
    val rank: Int,
    val name: String,
    val number: String, // Nomor start (misal #116)
    val team: String,
    val time: String,   // Total waktu
    val gap: String,    // Selisih dengan juara 1 (misal +2.04s)
    val vehicle: String
)

val dummyResults = listOf(
    RacerResultDummy(1, "Budi Santoso", "01", "Red Bull KTM", "14:20.05", "-", "KTM 250 EXC"),
    RacerResultDummy(2, "Doni Tata", "07", "Yamaha Monster", "14:22.10", "+2.05s", "YZ250F"),
    RacerResultDummy(3, "Farhan Hendro", "16", "Husqvarna ID", "14:25.55", "+5.50s", "FC 250"),
    RacerResultDummy(4, "Aldi Satya", "99", "Honda Racing", "14:30.00", "+9.95s", "CRF250R"),
    RacerResultDummy(5, "Dimas Ekky", "20", "Pertamina Mandalika", "14:45.12", "+25.07s", "KTM 450"),
    RacerResultDummy(6, "Gerry Salim", "31", "Astra Honda", "15:10.00", "+49.95s", "CRF150L"),
    RacerResultDummy(7, "Topan Sucipto", "55", "Privater", "15:15.22", "+55.17s", "KLX 150"),
)
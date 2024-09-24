package com.example.warthundervehicles.data.remote.apimodels.version2

data class WeaponX(
    val ammos: List<AmmoX>,
    val count: Int,
    val name: String,
    val weapon_type: String
) {
    fun cleanWeaponName(): Pair<String, Boolean> {

        var isTurret: Boolean = false
        var name = name
        val cadenas =
            listOf<String>(
                "gun",
                "_early",
                "turret",
                "_usaaf",
                "cannon",
                "_assault",
                "_1935",
                "user",
                "raf",
                "_for",
                "_"
            )
        //      12_7mm_M2_HB_user_machinegun_for_M15, 12_7mm_m2_browning_naval_user_machinegun, 37mm_M1A2_user_cannon, 7_62mm_M1919A4_user_machinegun, 37mm_M6_user_cannon, 75mm_M2_howitzer_user_cannon, 12_7mm_M2_HB_user_machinegun, 37mm_M3_user_cannon, 37mm_M5_early_user_cannon, gunType89, 40mm_l60_bofors_mk3_single_naval_user_cannon, gunType97_turret, gunType92_turret, gunType97, 6_5mm_type_38_1907_naval_user_machinegun, 37mm_type_11_naval_user_cannon, 7_7mm_type_89_naval_user_machinegun, 120mm_45_type_3_naval_user_cannon, 127mm_5_50_type_3_naval_user_cannon, 13_2mm_76_type93_naval_user_machinegun, 7_7mm_80_type_92_naval_user_machinegun, 120mm_Schneider_Canet_1898_user_cannon, 7_7mm_Type_97_user_machinegun, 25mm_60_type96_naval_user_cannon, 75mm_Type99_user_cannon, 37mm_Type1_user_cannon, 20mm_type_98_naval_user_cannon, 75mm_type_88_aa_naval_user_cannon, 37mm_type_94_user_cannon, 150mm_Type_38_user_cannon, 47mm_Type1_user_cannon, 20mm_Type98_user_cannon, 6_5mm_Type_91_user_machinegun, 57mm_Type90_user_cannon, 57mm_Type97_user_cannon, 70mm_Type94_user_cannon, 37mm_Type100_user_cannon, gunHo103, gunType98_turret, cannonHo3, cannonType94, cannonType88, gunHo104_turret, gunTe-1_turret, gunType89s_turret, gunType89_turret, gunVikkersK, gunVikkersE, gunBrowning303_raf, 12_7mm_vickers_mk5_naval_user_machinegun, 40mm_50_2pdr_rolls_royce_mk14_naval_user_cannon, 7_92mm_BESA_user_machinegun, 40mm_QF_2pdr_user_cannon, 7_7mm_Vikkers_user_machinegun, 47mm_OQF_3pdr_user_cannon, 95mm_QF_Mk1_user_cannon, 20mm_Oerlikon_for_tutorial_user_cannon, 76mm_3_40_12cwt_qf_mk5_naval_user_cannon, 102mm_45_4in_bl_mark9_10_naval_user_cannon, 102mm_45_4in_qf_mark5_naval_user_cannon, 120mm_45_qf_mk12_mount_cp17_single_naval_user_cannon, 102mm_45_4in_qf_mark16_21_naval_user_cannon, 40mm_39_2pdr_qf_mk8_pom_pom_naval_user_cannon, 20mm_oerlikon_mk24_twin_mount_naval_user_cannon, 76mm_45_3in_20cwt_qf_mk1_4_naval_user_cannon, 7_7mm_lewis_amg_1916_naval_user_machinegun, 47mm_qf3pdr_hotchkiss_naval_user_cannon, 7_7mm_vickers_go_no5_naval_user_machinegun, 7_7mm_mg_lewis_naval_user_machinegun, 7_92mm_BESA_AA_user_machinegun, gunVikkersK_turret, cannonHispano404, gunMAC34T_turret, gunMAC34, cannonHispano_hs9, gunMle33, gunDarne33, gunMAC39_turret, 47mm_SA_34_user_cannon, 7_5mm_MAC_31_user_machinegun, 47mm_SA_35_user_cannon, 25mm_SA_34_user_cannon, 8mm_Hotchkiss_Mle1914_user_machinegun, 155mm_Schneider_155_C_user_cannon, 13_2mm_Hotchkiss_Mle_1930_user_machinegun, 37mm_50_model1933_naval_user_cannon, 130mm_40_model_1919_naval_user_cannon, 40mm_39_2pdr_qf_mk2c_naval_user_cannon, 75mm_50_model_1922_aa_naval_user_cannon, 13_2mm_76_model1929_naval_user_machinegun, 37mm_SA_18_user_cannon, 37mm_SA_38_user_cannon, 47mm_SA_37_user_cannon, 75mm_APX_user_cannon, gunMle38, gunBrowningFN_french, gunMAC34_turret, gunMAC39, gunMG81_turret, cannonMGFF, gunMG17, gunAkan_m40, gunBrowningM36No3, gunKsp_m22, gunKsp_m22_37r_turret, gunKsp_m22_fv, gunKsp_m22_fh, 114mm_Psv_H_18_user_cannon, 75mm_kan_m41_open_turret_user_cannon, 8mm_Ksp_m36_user_machinegun, 20mm_Pvlv_kan_m40_user_cannon, 20mm_Lva_kan_m40_B_user_cannon, 75mm_kan_m02_user_cannon, 6_5mm_Ksp_m14_29_user_machinegun, 37mm_kan_m38_user_cannon, 8mm_Ksp_m39_B_user_machinegun, 37mm_Psv_K36_user_cannon, 88mm_Kwk43_for_tutorial_user_cannon, 7_92mm_MG34_user_machinegun, gunUB_turret, cannonGAU_13A, 76mm_M1_user_cannon, 82mm_m8_launcher_user_cannon, 12_7mm_dshk_1938_naval_user_machinegun, 130mm_50_b13_1936_naval_user_cannon, 37mm_67_70k_single_naval_user_cannon, 76mm_55_34k_naval_user_cannon, 76mm_1914_lender_naval_user_cannon, 102mm_60_1911_naval_user_cannon, 45mm_46_21k_naval_user_cannon, 76mm_55_39k_naval_user_cannon, gunMG15_turret, gunMG131_turret, cannonMG15120_t

        cadenas.forEach {
            if (name.contains(it)) {
                name = when (it) {
                    "turret" -> {
                        isTurret = true
                        name.replace(it, "")
                    }
                    "cannon" -> if (!name.contains("naval")) name.replace(it, "") else name
                    "_" -> name.replace(it, " ")
                    else -> name.replace(it, "")
                }
            }
        }
        val regex = "(\\d+)".toRegex()
        name = regex.replace(name) { " ${it.value} " }.trim()

        return Pair(name,isTurret)

    }

}
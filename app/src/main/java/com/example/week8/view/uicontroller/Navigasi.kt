package com.example.week8.view.uicontroller


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.week8.model.DataJK.JenisK
import com.example.week8.view.FormIsian
import com.example.week8.view.TampilData
import com.example.week8.viewmodel.SiswaViewModel
import kotlin.collections.map

enum class Navigasi {
    Formulirku,
    Detail
}

@Composable
fun DataApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: SiswaViewModel = viewModel ()
){
    Scaffold { isiRuang->
        val uiState = viewModel.statusUI.collectAsState()
        NavHost(
            navController = navController,
            startDestination = Navigasi.Formulirku.name,

            modifier = Modifier.padding(paddingValues = isiRuang)){
            composable(route = Navigasi.Formulirku.name){
                val konteks = LocalContext.current
                FormIsian (
                    pilihanJK = JenisK.map { id -> konteks.resources.getString(id)},
                    OnSubmitBtnClick = {
                        viewModel.setSiswa(it)
                        navController.navigate(Navigasi.Detail.name)
                    }
                )
            }
            composable(route = Navigasi.Detail.name) {
                TampilData(
                    statusUISiswa = uiState.value,
                    onBackBtnClick = {
                        cancelAndBackToFormulirku(navController)
                    }
                )
            }

        }
    }
}

private fun cancelAndBackToFormulirku(navController: NavHostController) {
    navController.popBackStack(route = Navigasi.Formulirku.name,
        inclusive = false)
}
/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat

// Clase principal que representa una actividad de la app
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Habilita el diseño de borde a borde (sin márgenes del sistema)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Establece el contenido de la UI usando Jetpack Compose
        setContent {
            // Aplica el tema de la app
            TipTimeTheme {
                // Superficie que ocupa toda la pantalla
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    // Llama a la función que define el diseño de la pantalla principal
                    TipTimeLayout()
                }
            }
        }
    }
}

// Función que define el diseño de la pantalla principal usando Jetpack Compose
@Composable
fun TipTimeLayout() {
    Column(
        modifier = Modifier
            .statusBarsPadding() // Añade padding para evitar que el contenido se solape con la barra de estado
            .padding(horizontal = 40.dp) // Padding horizontal
            .safeDrawingPadding(), // Padding seguro para evitar interferencias con áreas del sistema
        horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos horizontalmente
        verticalArrangement = Arrangement.Center // Centra los elementos verticalmente
    ) {
        // Título o encabezado: "Calculate Tip"
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp) // Espaciado arriba y abajo
                .align(alignment = Alignment.Start) // Alineación al inicio (izquierda)
        )

        // Campo de texto para ingresar el monto de la cuenta
        EditNumberField(modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth())

        // Texto que muestra el resultado de la propina formateado
        Text(
            text = stringResource(R.string.tip_amount, "$0.00"),
            style = MaterialTheme.typography.displaySmall
        )

        // Espaciador vertical (150dp de alto)
        Spacer(modifier = Modifier.height(150.dp))
    }
}

// Función que crea un campo de texto (aún sin lógica de entrada)
@Composable
fun EditNumberField(modifier: Modifier = Modifier) {
    TextField(
        value = "", // Valor actual del campo (vacío por defecto)
        onValueChange = {}, // Función que manejará cambios en el texto (vacía por ahora)
        modifier = modifier
    )
}

/**
 * Función que calcula la propina según el monto y el porcentaje ingresado.
 * Retorna la propina en formato de moneda local (ej. "$10.00").
 */
private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

// Vista previa del diseño en el editor de Android Studio
@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    TipTimeTheme {
        TipTimeLayout()
    }
}

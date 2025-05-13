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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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

@Composable
fun TipTimeLayout() {
    // Variable de estado para guardar la entrada del usuario como texto
    var amountInput by remember { mutableStateOf("") }

    // Convierte la entrada a Double si es posible, si no, usa 0.0 como valor por defecto
    val amount = amountInput.toDoubleOrNull() ?: 0.0

    // Calcula la propina usando la función calculateTip
    val tip = calculateTip(amount)

    // Contenedor en columna para organizar los elementos verticalmente
    Column(
        modifier = Modifier
            .statusBarsPadding() // Añade espacio para la barra de estado
            .padding(horizontal = 40.dp) // Padding horizontal de 40dp
            .verticalScroll(rememberScrollState()) // Habilita scroll si el contenido es largo
            .safeDrawingPadding(), // Padding seguro para áreas del sistema
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontal
        verticalArrangement = Arrangement.Center // Centrado vertical
    ) {
        // Texto de encabezado: "Calculate Tip"
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp) // Espaciado superior e inferior
                .align(alignment = Alignment.Start) // Alineación a la izquierda
        )

        // Campo de texto para introducir el monto de la cuenta
        EditNumberField(
            value = amountInput, // Valor actual del texto
            onValueChange = { amountInput = it }, // Actualiza la variable de estado
            modifier = Modifier
                .padding(bottom = 32.dp) // Espaciado inferior
                .fillMaxWidth() // Ocupa todo el ancho disponible
        )

        // Muestra el resultado de la propina calculada
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall // Aplica estilo de texto del tema
        )

        // Espaciador para dar separación inferior
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun EditNumberField(
    value: String, // Valor actual del campo de texto
    onValueChange: (String) -> Unit, // Función que se llama cuando cambia el texto
    modifier: Modifier = Modifier // Modificador opcional
) {
    // Campo de texto para ingresar números
    TextField(
        value = value, // Muestra el texto actual
        onValueChange = onValueChange, // Actualiza el texto cuando cambia
        singleLine = true, // Limita a una sola línea
        label = { Text(stringResource(R.string.bill_amount)) }, // Etiqueta del campo: "Bill Amount"
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Teclado numérico
        modifier = modifier // Aplica los modificadores recibidos
    )
}

/**
 * Calcula la propina en base al monto ingresado y el porcentaje de propina.
 * @param amount Monto total de la cuenta.
 * @param tipPercent Porcentaje de propina a aplicar (por defecto 15%).
 * @return Una cadena con el valor de la propina formateado en moneda local.
 */
private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
    // Calcula la propina multiplicando el porcentaje por el monto
    val tip = tipPercent / 100 * amount

    // Formatea la propina como una cadena en formato de moneda (por ejemplo: "$3.00")
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
package ru.example.memesapp

import android.app.Application

class MainApp: Application() {
    val component by lazy { Component() }
}
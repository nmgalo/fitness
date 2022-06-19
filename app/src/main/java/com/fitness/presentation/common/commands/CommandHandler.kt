package com.fitness.presentation.common.commands


interface CommandHandler {

    fun handleCommand(command: BaseCommand){}

}
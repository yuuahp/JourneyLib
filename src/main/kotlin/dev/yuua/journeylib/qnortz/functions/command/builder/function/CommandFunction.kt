package dev.yuua.journeylib.qnortz.functions.command.builder.function

import dev.yuua.journeylib.qnortz.rules.RulesFunction
import dev.yuua.journeylib.qnortz.functions.command.CommandFromType
import dev.yuua.journeylib.qnortz.functions.command.event.UnifiedCommandInteractionEvent
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

data class CommandFunction(
    val slashFunction: SlashFunction?,
    val textFunction: TextFunction?,
    val options: List<OptionData>,
    val rules: List<RulesFunction<UnifiedCommandInteractionEvent>>,
    val acceptedOn: List<ChannelType>
) {
    private val illegalArgs = IllegalArgumentException("One of them must be null and the other must be not null!")

    val packageName: String

    val type = when {
        slashFunction != null -> CommandFromType.SlashCommand
        textFunction != null -> CommandFromType.TextCommand
        else -> throw illegalArgs
    }

    init {
        if ((slashFunction != null && textFunction != null) || (slashFunction == null && textFunction == null))
            throw illegalArgs

        packageName = when (type) {
            CommandFromType.TextCommand -> textFunction!!::class.java.packageName
            CommandFromType.SlashCommand -> slashFunction!!::class.java.packageName
        }
    }
}
package dev.yuua.journeylib.discord.framework.command.builder.structure

import dev.yuua.journeylib.discord.framework.command.builder.function.FrChecks
import dev.yuua.journeylib.discord.framework.command.builder.function.FrChecksResult
import dev.yuua.journeylib.discord.framework.command.builder.function.FrSlashFunction
import dev.yuua.journeylib.discord.framework.command.builder.function.FrTextFunction
import dev.yuua.journeylib.discord.framework.command.builder.option.FrOption
import dev.yuua.journeylib.discord.framework.command.event.FrCmdEvent
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData

class FrSubcmd(val name: String, val details: String, vararg val alias: String) {

    val jdaSubcmdData = SubcommandData(name, details)

    var function: FrSlashFunction? = null
    var textFunction: FrTextFunction? = null
    val options = mutableListOf<OptionData>()
    var checks: FrChecks? = null

    fun addOptions(vararg options: OptionData): FrSubcmd {
        this.options.addAll(options)
        jdaSubcmdData.addOptions(*options)
        return this
    }

    inline fun <reified T> addOption(
        name: String,
        details: String,
        required: Boolean = false,
        autoComplete: Boolean = false,
        builder: OptionData.() -> Unit = {}
    ): FrSubcmd {
        val option = FrOption<T>(name, details, required, autoComplete, builder)
        this.options.add(option)
        jdaSubcmdData.addOptions(option)
        return this
    }


    fun setFunction(function: FrSlashFunction): FrSubcmd {
        this.function = function
        return this
    }

    fun setFunction(function: FrTextFunction): FrSubcmd {
        this.textFunction = function
        return this
    }

    fun setChecks(checks: (FrCmdEvent) -> FrChecksResult): FrSubcmd {
        this.checks = FrChecks { checks(it) }
        return this
    }
}

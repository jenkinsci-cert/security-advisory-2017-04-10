/*

It is very likely this this produces an INCOMPLETE list of affected
uses of this plugin. It is only intended to give you a general idea
of how much upgrading this plugin will affect your instance.

*/

import hudson.util.VersionNumber
import hudson.model.*
import hudson.*
import jenkins.model.*
import jenkins.*

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

def pluginName = "warnings"
def fixedVersion = "4.61"

void check(Item item) {
	if (!(item instanceof AbstractProject)) {
		return
	}
	def pub = item.publishersList.get(hudson.plugins.warnings.WarningsPublisher)
	if (pub == null) {
		return
	}
	pub.consoleParsers.each { parserConfig ->
		def parser = hudson.plugins.warnings.parser.ParserRegistry.getParser(parserConfig.parserName)
		if (parser instanceof hudson.plugins.warnings.parser.DynamicParser) {
			noteItem(item, 'Warnings: Console Parser: ' + parser)
		}
	}
	pub.parserConfigurations.each { parserConfig ->
		def parser = hudson.plugins.warnings.parser.ParserRegistry.getParser(parserConfig.parserName)
		if (parser instanceof hudson.plugins.warnings.parser.DynamicParser) {
			noteItem(item, 'Warnings: File Parser: ' + parser)
		}
	}
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

boolean noteItem(Item item, def note) {
	println(item.getFullName() + ': ' + note?.toString())
}

if (Jenkins.instance.pluginManager.getPlugin(pluginName)?.isOlderThan(new VersionNumber(fixedVersion))?:false) {
	println "This instance has a vulnerable version of '" + pluginName + "' installed (older than " + fixedVersion + ")."
	Jenkins.instance.getAllItems().each { item ->
		check(item)
	}
}

return

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

def pluginName = "extensible-choice-parameter"
def fixedVersion = "1.4.0"

void check(Item item) {
	if (!(item instanceof Job)) {
		return
	}
	def prop = item.getProperty(hudson.model.ParametersDefinitionProperty)
	if (prop == null) {
		return
	}
	prop.parameterDefinitions.each { param ->
		if (param instanceof jp.ikedam.jenkins.plugins.extensible_choice_parameter.ExtensibleChoiceParameterDefinition) {
			// correct type, now check mode -- we don't look at 'enabled' to rather get false positives than false negatives
			if (param.choiceListProvider instanceof jp.ikedam.jenkins.plugins.extensible_choice_parameter.SystemGroovyChoiceListProvider) {
				noteItem(item, 'Extensible Choice Parameter: System Groovy Script')
			}
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

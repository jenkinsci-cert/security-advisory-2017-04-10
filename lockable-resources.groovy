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

def pluginName = "lockable-resources"
def fixedVersion = "2.0"

void check(Item item) {
	if (!(item instanceof Job)) {
		return
	}
	def prop = item.getProperty(org.jenkins.plugins.lockableresources.RequiredResourcesProperty)
	if (prop == null) {
		return
	}
	if (prop.labelName.startsWith("groovy:")) {
		noteItem(item, 'Lockable Resources: Groovy Label')
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

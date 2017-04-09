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

def pluginName = "groovy"
def fixedVersion = "2.0"

void checkProject(Item item) {
	if (!(item instanceof Project)) {
		return
	}
	def step = item.buildersList.get(hudson.plugins.groovy.SystemGroovy)
	if (step == null) {
		return
	}
	noteItem(item, "Groovy: System Groovy Build Step")
}

void checkMaven(Item item) {
	if (item.getClass().name != "hudson.maven.MavenModuleSet") {
		return
	}
	def step = item.postbuilders.get(hudson.plugins.groovy.SystemGroovy)
	if (step == null) {
		return
	}
	noteItem(item, "Groovy: System Groovy Build Step")
}

def check(Item item) {
	checkProject(item)
	checkMaven(item)
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

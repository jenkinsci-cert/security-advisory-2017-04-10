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

def pluginName = "email-ext"
def fixedVersion = "2.57.2"

void check(Item item) {
	if (!(item instanceof AbstractProject)) {
		return
	}
	def pub = item.publishersList.get(hudson.plugins.emailext.ExtendedEmailPublisher)
	if (pub == null) {
		return
	}
	if (pub.presendScript != null && pub.presendScript.trim() != '' && pub.presendScript.trim() != '$DEFAULT_PRESEND_SCRIPT') {
		noteItem(item, 'Email Extension: Pre-Send Script')
	}
	if (pub.postsendScript != null && pub.postsendScript.trim() != '' && pub.postsendScript.trim() != '$DEFAULT_POSTSEND_SCRIPT') {
		noteItem(item, 'Email Extension: Post-Send Script')
	}
	if (pub.classpath != null && !pub.classpath.isEmpty()) {
		noteItem(item, 'Email Extension: Classpath')
	}
	if (pub.defaultContent != null && (pub.defaultContent.contains('${SCRIPT') || pub.defaultContent.contains('${JELLY_SCRIPT'))) {
		noteItem(item, 'Email Extension: Template Use in Default Body')
	}
	pub.configuredTriggers.each { trigger ->
		if (trigger instanceof hudson.plugins.emailext.plugins.trigger.AbstractScriptTrigger) {
			noteItem(item, 'Email Extension: ' + trigger.descriptor.displayName)
		}
		if (trigger.email.body != null && (trigger.email.body.contains('${SCRIPT') || trigger.email.body.contains('${JELLY_SCRIPT'))) {
			noteItem(item, 'Email Extension: Template Use in Body of Trigger: ' + trigger.descriptor.displayName)
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

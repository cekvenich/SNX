package org.SNXa.ssr

import java.util.HashMap

import java.util.Map

import de.neuland.jade4j.JadeConfiguration

import de.neuland.jade4j.template.FileTemplateLoader

import de.neuland.jade4j.template.JadeTemplate

import de.neuland.jade4j.template.TemplateLoader

//remove if not needed
import scala.collection.JavaConversions._

class Pug {

  var _render: JadeConfiguration = new JadeConfiguration()

  def Pug(): String = {
    val cwd: String = System.getProperty("user.dir")
    val loader: TemplateLoader =
      new FileTemplateLoader(cwd + "/routes/", "UTF-8")
    _render.setTemplateLoader(loader)
    _render.setCaching(false)
    val template1: JadeTemplate = _render.getTemplate("index.pug")

    val model1: java.util.Map[String, Object] = new java.util.HashMap[String, Object]()
    model1.put("city", "Bremen")
    //binding
    val html: String = _render.renderTemplate(template1, model1)
    println(html)
    html
  }

}

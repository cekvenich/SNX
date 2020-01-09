package s;

import java.util.HashMap;
import java.util.Map;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;
import de.neuland.jade4j.template.TemplateLoader;

public class Pug {

	static JadeConfiguration _render = new JadeConfiguration();

	public Pug() throws Throwable {

		String cwd = System.getProperty("user.dir");
		TemplateLoader loader = new FileTemplateLoader(cwd+"/routes/", "UTF-8");
		_render.setTemplateLoader(loader);
		_render.setCaching(false);

		
		JadeTemplate template1 = _render.getTemplate("index.pug");

		Map<String, Object> model1 = new HashMap<String, Object>();
		model1.put("city", "Bremen");
		
		String html = _render.renderTemplate(template1, model1);
		System.out.println(html);

	}
}

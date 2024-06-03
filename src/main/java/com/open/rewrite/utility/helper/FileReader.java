package com.open.rewrite.utility.helper;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.core.env.PropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

@Component
public class FileReader {

	@Value("${openrewrite.active.recipe}")
	private String openrewrite_active_recipe;

	@Autowired
	Environment env;

	Map<String, Object> map = new HashMap();
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public void load() {
		if (env instanceof ConfigurableEnvironment) {
			for (PropertySource<?> propertySource : ((ConfigurableEnvironment) env).getPropertySources()) {
				if (propertySource instanceof EnumerablePropertySource) {
					for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
						map.put(key, propertySource.getProperty(key));
					}
				}
			}
		}
	}

	public void executeReceipe(File[] files) {
		logger.info("OpenReqrite ACTIVE Recipe-> " + openrewrite_active_recipe);
		load();
		int i = 0;
		String activeRecipe = (String) map.get(openrewrite_active_recipe);
		for (File file : files) {
			if (file.isDirectory()) {
				try {
					Process process = Runtime.getRuntime().exec("cmd /c start " + activeRecipe + "", null,
							new File(file.getAbsolutePath()));
					logger.info("process initited count " + i++ + " " + process.isAlive() + " - "+file.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {

			}
		}
	}

	public String getOpenrewrite_active_recipe() {
		return openrewrite_active_recipe;
	}

	public void setOpenrewrite_acive_recipe(String openrewrite_active_recipe) {
		this.openrewrite_active_recipe = openrewrite_active_recipe;
	}

}

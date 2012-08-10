package org.expressme.examples.typecho.dao;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.expressme.examples.typecho.entity.CityBus;

public class CityBusDict {
	private static Logger logger  =  Logger.getLogger(CityBusDict.class);
	public static void dict() {
		try {
			List<String> readLines = FileUtils.readLines(new File(CityBusDict.class.getResource("/citybus/citybus.txt").toURI()), "utf8");
			for (String line : readLines) {
				line = StringUtils.trimToEmpty(line);
				if (StringUtils.isEmpty(line) || line.startsWith("$"))
					continue;
				CityBus cityBus = new CityBus();
				cityBus.split(line);
				logger.debug(cityBus.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package org.expressme.examples.typecho.entity;

import org.apache.commons.lang.StringUtils;

import lombok.Data;


/**
 * Store item.
 * {@link http://www.dushixing.com.cn}
 * @author xiaoli (mengfan0871@gmail.com)
 */
public @Data class CityBus {
	// 格式：名称|运营时间|备注 = 站点1,站点2……
	// 对运营时间的说明：*表示不考虑，00:00-24:00标识24小时运营
	private String id;
	private String name;
	private String up;
	private String description;
	// 开始、结束时间
	private String starting;
	private String ending;
	private String route;
	
	public void split(String bus) {
		if (StringUtils.isEmpty(bus))
			return;
		String[] info = StringUtils.split(bus, '|');
		if (info.length != 3)
			return;
		if (info[0].endsWith("↑")) {
			name = info[0].substring(0, info[0].length() - 1);
			up = "↑";
		} else if (info[0].endsWith("↓")) {
			name = info[0].substring(0, info[0].length() - 1);
			up = "↓";
		} else {
			name = info[0];
			up = "↑↓";
		}
		if ("*".equals(info[1])) {
			starting = ending = info[1];
		} else {
			starting = info[1].substring(0, info[1].indexOf('-'));
			ending = info[1].substring(info[1].indexOf('-') + 1);
		}
		info = StringUtils.split(info[2], '=');
		if (info.length != 2)
			return;
		description = info[0];
		route = info[1];
	}
}


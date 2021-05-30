package net.sf.dynamicreports.reporting;
/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2015 Ricardo Mariaca
 * http://www.dynamicreports.org
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */


import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;

import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.dynamicreports.reporting.Templates;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)
 */
public class ColumnDataTypesReport {

	private DRDataSource dataSource;
	private String fileName;

	public ColumnDataTypesReport(DRDataSource dataSource, String fileName) {
		this.dataSource = dataSource;
		this.fileName = fileName;
	}

	public void build() throws FileNotFoundException {
		try {
			report()
			  .setTemplate(Templates.reportTemplate)
			  .columns(
			  	col.column("Result", "result", type.stringType()),
			  	col.column("Tab",    "tab",  type.stringType()),
			  	col.column("Comment",  "comment", type.stringType()))
			  .title(Templates.createTitleComponent("Autotest report"))
			  .pageFooter(Templates.footerComponent)
			  .setDataSource(dataSource)
			  .toPdf(new FileOutputStream(fileName));
			System.out.println("Saved report to the project root folder with name: " + fileName);
		} catch (DRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Report is opened, please close it first");
		}
	}
}

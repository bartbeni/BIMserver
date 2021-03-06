package org.bimserver.utils;

/******************************************************************************
 * Copyright (C) 2009-2018  BIMserver.org
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see {@literal<http://www.gnu.org/licenses/>}.
 *****************************************************************************/

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.plugins.deserializers.DeserializeException;
import org.bimserver.plugins.deserializers.Deserializer;

public class DeserializerUtils {

	public static IfcModelInterface readFromBytes(Deserializer deserializer, byte[] bytes, String filename) throws DeserializeException {
		return deserializer.read(new ByteArrayInputStream(bytes), filename, bytes.length, null);
	}

	public static IfcModelInterface readFromFile(Deserializer deserializer, Path path) throws DeserializeException, IOException {
		try (InputStream fis = Files.newInputStream(path)) {
			return deserializer.read(fis, path.getFileName().toString(), Files.size(path), null);
		}
	}
}
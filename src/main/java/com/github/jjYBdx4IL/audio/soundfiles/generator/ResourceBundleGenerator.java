/*
 * Copyright © 2017 jjYBdx4IL (https://github.com/jjYBdx4IL)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jjYBdx4IL.audio.soundfiles.generator;

import com.helger.jcodemodel.EClassType;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JClassAlreadyExistsException;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JEnumConstant;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JFieldVar;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.helger.jcodemodel.JVar;
import com.helger.jcodemodel.writer.FileCodeWriter;
import com.helger.jcodemodel.writer.ProgressCodeWriter;

import org.apache.tools.ant.DirectoryScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResourceBundleGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceBundleGenerator.class);

    public static void main(String[] args) {
        File scanRootDir = new File(args[2]);
        File outputRootDir = new File(args[3]);
        if (!outputRootDir.exists()) {
            if (!outputRootDir.mkdirs()) {
                throw new RuntimeException("failed to create output root directory" + outputRootDir.getPath());
            }
        }
        try {
            new ResourceBundleGenerator().generate(args[0], args[1], scanRootDir, outputRootDir);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void generate(String packageName, String typeSimpleName, File scanDirectory, File outputRootDir)
            throws JClassAlreadyExistsException, IOException {

        List<ResourceEntry> resources = new ArrayList<>();

        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[] { "**/*.ogg" });
        scanner.setBasedir(scanDirectory);
        scanner.setCaseSensitive(false);
        scanner.scan();

        for (String filePath : scanner.getIncludedFiles()) {
            File file = new File(scanner.getBasedir(), filePath);
            String resourceId = file.getName();
            resourceId = resourceId.substring(0, resourceId.length() - 4);
            String fileResourcePath = "/" + filePath.replace("\\", "/");
            resources.add(new ResourceEntry(resourceId, fileResourcePath));
        }

        if (resources.isEmpty()) {
            throw new RuntimeException("nothing found beneath " + scanDirectory.getAbsolutePath());
        }

        Collections.sort(resources);

        JCodeModel cm = new JCodeModel();
        JDefinedClass definedClass = cm._package(packageName)._class(JMod.PUBLIC, typeSimpleName, EClassType.ENUM);

        JFieldVar absResPathField = definedClass.field(JMod.PRIVATE | JMod.FINAL, String.class, "absoluteResourcePath");

        JMethod constructor = definedClass.constructor(JMod.PRIVATE);
        JVar param1 = constructor.param(String.class, "absoluteResourcePath");

        JBlock body = constructor.body();
        body.assign(JExpr._this().ref(absResPathField), param1);

        JMethod asUrlMethod = definedClass.method(JMod.PUBLIC, URL.class, "asUrl");
        asUrlMethod.body()._return(definedClass.dotclass().invoke("getResource").arg(absResPathField));

        JMethod asStreamMethod = definedClass.method(JMod.PUBLIC, InputStream.class, "asStream");
        asStreamMethod.body()._return(definedClass.dotclass().invoke("getResourceAsStream").arg(absResPathField));

        JMethod getAbsolutePathMethod = definedClass.method(JMod.PUBLIC, String.class, "getAbsolutePath");
        getAbsolutePathMethod.body()._return(absResPathField);

        for (ResourceEntry resourceEntry : resources) {
            JEnumConstant enumConst = definedClass.enumConstant(resourceEntry.getResourceId());
            enumConst.arg(JExpr.lit(resourceEntry.getAbsoluteResourcePath()));
            enumConst.javadoc().add("{@code " + resourceEntry.getAbsoluteResourcePath() + "}");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cm.build(new ProgressCodeWriter(new FileCodeWriter(outputRootDir), new PrintStream(baos) {
            @Override
            public void print(final String msg) {
                LOG.info("creating: " + msg);
            }
        }));
    }
}

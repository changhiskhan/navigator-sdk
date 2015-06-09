/*
 * Copyright (c) 2015 Cloudera, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudera.nav.plugin.examples.stetson2;

import com.cloudera.nav.plugin.client.NavApiCient;
import com.cloudera.nav.plugin.client.NavigatorPlugin;
import com.cloudera.nav.plugin.examples.stetson.CustomLineageCreator;
import com.cloudera.nav.plugin.model.MD5IdGenerator;
import com.cloudera.nav.plugin.model.Source;
import com.cloudera.nav.plugin.model.SourceType;

import org.joda.time.Instant;

/**
 * In this example we show a more complex example of how to create custom entity
 * types and how to link them to hadoop entities. For a description of the base
 * example please see {@link com.cloudera.nav.plugin.examples.stetson.CustomLineageCreator}.
 *
 * As an extension of the previous example, we allow the user to specify custom
 * input and output datasets to a StetsonExecution. We define a new custom
 * entity called StetsonDataset. This is a logical dataset in the Stetson
 * application and is physically represented by an HDFS directory.
 *
 * New relationships:
 *
 * StetsonDataset input ---(LogicalPhysical)---> HDFS directory
 * StetsonDataset output ---(LogicalPhysical)---> HDFS directory
 * StetsonDataset input ---(DataFlow)---> StetsonExecution
 * StetsonExecution ---(DataFlow)---> StetsonDataset output
 */
public class CustomLineageCreator2 extends CustomLineageCreator {

  public static void main(String[] args) {
    CustomLineageCreator2 lineageCreator = new CustomLineageCreator2(args[0]);
    lineageCreator.run();
  }

  public CustomLineageCreator2(String configFilePath) {
    super(configFilePath);
  }

  @Override
  protected StetsonExecution2 createExecution() {
    StetsonExecution2 exec = new StetsonExecution2(plugin.getNamespace());

    // Change according to actual execution. Use the PigIdGenerator to generate
    // the correct identities based on the job name and the pig.script.id
    // from the job conf
    String pigExecutionId = "f3603812e2c4d95e7e6bbc9afbabc160";
    exec.setPigExecution(pigExecutionId);

    exec.setName("Stetson Execution");
    exec.setDescription("I am a custom operation instance");
    exec.setLink("http://hasthelargehadroncolliderdestroyedtheworldyet.com/");
    exec.setStarted(Instant.now());
    exec.setEnded((new Instant(Instant.now().toDate().getTime() + 10000)));
    // Extend the previous stetson example by linking it to inputs and outputs
    String inputName = "StetsonInput"; // Stetson's name for the input dataset
    String outputName = "StetsonOutput"; // Stetson's name for the output data
    String inputPath = "/datasets/input"; // path of HDFS dir for input dataset
    String outputPath = "/datasets/output"; // path of HDFS dir for output dataset
    exec.addInput(inputName, getHdfsEntityId(inputPath, plugin));
    exec.addOutput(outputName, getHdfsEntityId(outputPath, plugin));
    return exec;
  }

  private static String getHdfsEntityId(String path, NavigatorPlugin plugin) {
    NavApiCient client = plugin.getClient();
    Source hdfs = client.getOnlySource(SourceType.HDFS);
    return MD5IdGenerator.generateIdentity(hdfs.getIdentity(), path);
  }
 }

/*
 * SonarQube
 * Copyright (C) 2009-2023 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.scanner.sensor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.api.SonarEdition;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.batch.fs.InputModule;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.DefaultInputProject;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.rule.internal.ActiveRulesBuilder;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.internal.SonarRuntimeImpl;
import org.sonar.api.utils.Version;
import org.sonar.scanner.cache.AnalysisCacheEnabled;
import org.sonar.scanner.cache.ReadCacheImpl;
import org.sonar.scanner.cache.WriteCacheImpl;
import org.sonar.scanner.scan.branch.BranchConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ModuleSensorContextTest {

  @Rule
  public TemporaryFolder temp = new TemporaryFolder();

  private final ActiveRules activeRules = new ActiveRulesBuilder().build();
  private final MapSettings settings = new MapSettings();
  private final DefaultSensorStorage sensorStorage = mock(DefaultSensorStorage.class);
  private final BranchConfiguration branchConfiguration = mock(BranchConfiguration.class);
  private final WriteCacheImpl writeCache = mock(WriteCacheImpl.class);
  private final ReadCacheImpl readCache = mock(ReadCacheImpl.class);
  private final AnalysisCacheEnabled analysisCacheEnabled = mock(AnalysisCacheEnabled.class);
  private final UnchangedFilesHandler unchangedFilesHandler = mock(UnchangedFilesHandler.class);
  private final SonarRuntime runtime = SonarRuntimeImpl.forSonarQube(Version.parse("5.5"), SonarQubeSide.SCANNER, SonarEdition.COMMUNITY);
  private DefaultFileSystem fs;
  private ModuleSensorContext adaptor;

  @Before
  public void prepare() throws Exception {
    fs = new DefaultFileSystem(temp.newFolder().toPath());
    adaptor = new ModuleSensorContext(mock(DefaultInputProject.class), mock(InputModule.class), settings.asConfig(), settings, fs, activeRules, sensorStorage, runtime,
      branchConfiguration, writeCache, readCache, analysisCacheEnabled, unchangedFilesHandler);
  }

  @Test
  public void shouldProvideComponents() {
    assertThat(adaptor.activeRules()).isEqualTo(activeRules);
    assertThat(adaptor.fileSystem()).isEqualTo(fs);
    assertThat(adaptor.getSonarQubeVersion()).isEqualTo(Version.parse("5.5"));
    assertThat(adaptor.runtime()).isEqualTo(runtime);
    assertThat(adaptor.canSkipUnchangedFiles()).isFalse();

    assertThat(adaptor.nextCache()).isEqualTo(writeCache);
    assertThat(adaptor.previousCache()).isEqualTo(readCache);

    assertThat(adaptor.newIssue()).isNotNull();
    assertThat(adaptor.newExternalIssue()).isNotNull();
    assertThat(adaptor.newAdHocRule()).isNotNull();
    assertThat(adaptor.newMeasure()).isNotNull();
    assertThat(adaptor.newAnalysisError()).isEqualTo(ModuleSensorContext.NO_OP_NEW_ANALYSIS_ERROR);
    assertThat(adaptor.isCancelled()).isFalse();
    assertThat(adaptor.newSignificantCode()).isNotNull();
  }

  @Test
  public void should_delegate_to_unchanged_files_handler() {
    DefaultInputFile defaultInputFile = mock(DefaultInputFile.class);
    adaptor.markAsUnchanged(defaultInputFile);

    verify(unchangedFilesHandler).markAsUnchanged(defaultInputFile);
  }

  @Test
  public void pull_request_can_skip_unchanged_files() {
    when(branchConfiguration.isPullRequest()).thenReturn(true);
    adaptor = new ModuleSensorContext(mock(DefaultInputProject.class), mock(InputModule.class), settings.asConfig(), settings, fs, activeRules, sensorStorage, runtime,
      branchConfiguration, writeCache, readCache, analysisCacheEnabled, unchangedFilesHandler);
    assertThat(adaptor.canSkipUnchangedFiles()).isTrue();
  }

}
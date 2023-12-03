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
import * as React from 'react';
import withAvailableFeatures, {
  WithAvailableFeaturesProps,
} from '../../../app/components/available-features/withAvailableFeatures';
import { Feature } from '../../../types/features';
import { Component } from '../../../types/types';
import { BuildTools } from '../types';
import CFamily from './commands/CFamily';
import DotNet from './commands/DotNet';
import Gradle from './commands/Gradle';
import JavaMaven from './commands/JavaMaven';
import Others from './commands/Others';

export interface AnalysisCommandProps extends WithAvailableFeaturesProps {
  buildTool: BuildTools;
  mainBranchName: string;
  component: Component;
  onDone: () => void;
}

export function AnalysisCommand(props: AnalysisCommandProps) {
  const { buildTool, component, mainBranchName } = props;
  const branchSupportEnabled = props.hasFeature(Feature.BranchSupport);

  if (!buildTool) {
    return null;
  }

  switch (buildTool) {
    case BuildTools.Maven:
      return (
        <JavaMaven
          branchesEnabled={branchSupportEnabled}
          mainBranchName={mainBranchName}
          component={component}
          onDone={props.onDone}
        />
      );
    case BuildTools.Gradle:
      return (
        <Gradle
          branchesEnabled={branchSupportEnabled}
          mainBranchName={mainBranchName}
          component={component}
          onDone={props.onDone}
        />
      );
    case BuildTools.DotNet:
      return (
        <DotNet
          branchesEnabled={branchSupportEnabled}
          mainBranchName={mainBranchName}
          component={component}
          onDone={props.onDone}
        />
      );
    case BuildTools.CFamily:
      return (
        <CFamily
          branchesEnabled={branchSupportEnabled}
          mainBranchName={mainBranchName}
          component={component}
          onDone={props.onDone}
        />
      );
    case BuildTools.Other:
      return (
        <Others
          branchesEnabled={branchSupportEnabled}
          mainBranchName={mainBranchName}
          component={component}
          onDone={props.onDone}
        />
      );
  }
}

export default withAvailableFeatures(AnalysisCommand);
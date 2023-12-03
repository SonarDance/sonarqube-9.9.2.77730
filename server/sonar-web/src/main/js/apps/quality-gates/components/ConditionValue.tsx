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
import classNames from 'classnames';
import * as React from 'react';
import { formatMeasure } from '../../../helpers/measures';
import { Condition, Metric } from '../../../types/types';
import { getCorrectCaycCondition, isCaycCondition } from '../utils';
import ConditionValueDescription from './ConditionValueDescription';

interface Props {
  condition: Condition;
  isCaycModal?: boolean;
  metric: Metric;
  isCaycCompliantAndOverCompliant?: boolean;
}

function ConditionValue({
  condition,
  isCaycModal,
  metric,
  isCaycCompliantAndOverCompliant,
}: Props) {
  if (isCaycModal) {
    const isToBeModified = condition.error !== getCorrectCaycCondition(condition).error;

    return (
      <>
        {isToBeModified && (
          <span className="red-text strike-through spacer-right">
            {formatMeasure(condition.error, metric.type)}
          </span>
        )}
        <span className={classNames('spacer-right', { 'green-text': isToBeModified })}>
          {formatMeasure(getCorrectCaycCondition(condition).error, metric.type)}
        </span>
        <ConditionValueDescription
          className={classNames({ 'green-text': isToBeModified })}
          condition={getCorrectCaycCondition(condition)}
          metric={metric}
        />
      </>
    );
  }

  return (
    <>
      <span className="spacer-right">{formatMeasure(condition.error, metric.type)}</span>
      {isCaycCompliantAndOverCompliant && isCaycCondition(condition) && (
        <ConditionValueDescription condition={condition} metric={metric} />
      )}
    </>
  );
}

export default ConditionValue;

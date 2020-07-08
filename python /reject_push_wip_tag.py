#!/usr/bin/env python3
"""This scripts aims not to allow Git push code to remote branches if at least one test scenario is marked with specific tag @wip
"""
import os
import sys



def scenario_with_wip_hook():
    red_color = '\033[91m'
    features_path = '/src/test/resources/features/patient/'
    scenarios_wip = collect_scenarios_wip(features_path)
    if scenarios_wip:
        print("\n" + red_color + "REMOVE @WIP IN ORDER TO PUSH:\n")
        print("------------------------------------------------------------------")
        for feature, scenario in scenarios_wip:
            print(feature.replace(features_path, "\n"), scenario + "\n")
            print("")
        print("------------------------------------------------------------------")
        return 1
    return 0

def collect_scenarios_wip(feature_path):
    """Parse all feature files by supplied feature path storage, in order to identify @wip before Scenario or Feature

    :param feature_path: Relative path of feature files storage
    :return scenarios_with_wip list of tuples - (feature, scenario)
    """
    scenarios_with_wip = []
    path = os.getcwd() + feature_path
    features = (path + name for name in os.listdir(path) if name.endswith('.feature')) # generator expression with finding only feature files
    for feature in features:
        with open(feature, 'r', encoding='utf-8') as lines:
            feature_lines = tuple(lines)
        index_line_with_wip = (feature_lines.index(line) for line in feature_lines if '@wip' in line) # generator expression with finding @wip predicate
        for index in index_line_with_wip:
            scenarios_with_wip.append((feature.replace(os.getcwd(), ""), feature_lines[index + 1])) # need to +1 to index as the line after @wip is informative
    return scenarios_with_wip

if __name__ == '__main__':
    sys.exit(scenario_with_wip_hook())

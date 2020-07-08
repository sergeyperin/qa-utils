#!/usr/bin/env python3
import json, os, sys
import xml.etree.ElementTree


"""Allows to collect json report file after the scenarios pack being executed, identify the amount of failed scenarios
and process the new report json with only failed scenarios
Later you can set up this json report to run again.
"""

report_path_list = []
rerun_tests_list = []
if len(sys.argv) > 1:
    report_path_list = sys.argv[1:]


def json_updater(json_report_path):
    if os.path.exists(json_report_path):
        print("Report %s found, deleting failed features" % json_report_path)

        with open(json_report_path, "r") as jsonFile:
            json_data = json.load(jsonFile)

        if len(json_data):
            print("JSON data found, deleting failed features")
            for current_scenario in json_data:
                features_to_delete = []
                features_list = current_scenario['elements']
                for current_feature in features_list:
                    for step in current_feature['steps']:
                        if step['result']['status'] == "failed" or step['result']['status'] == "skipped":
                            features_to_delete.append(current_feature)
                            break
                for feature_to_delete in features_to_delete:
                    current_scenario['elements'].remove(feature_to_delete)
                    print('Failed feature "%s" deleted' % feature_to_delete['name'])

            empty_scenarios = []
            for current_scenario in json_data:
                if len(current_scenario['elements']) == 0:
                    empty_scenarios.append(current_scenario)
            for scenario_to_delete in empty_scenarios:
                json_data.remove(scenario_to_delete)
                print("Scenario %s deleted, due to it hasn't success features" % scenario_to_delete['name'])

            with open(json_report_path, "w") as jsonFile:
                json.dump(json_data, jsonFile)
            print("Json report %s rewritten" % json_report_path)


def xml_updater(xml_report_path):
    if os.path.exists(xml_report_path):
        print("Report %s found, deleting failed features" % xml_report_path)
    tree = xml.etree.ElementTree.parse(xml_report_path)
    tree.getroot().attrib['skipped'] = \
        str(int(tree.getroot().attrib['skipped']) + int(tree.getroot().attrib['failures']))
    tree.getroot().attrib['failures'] = '0'
    for node in tree.getroot():
        for test in node:
            if test.tag == "failure":
                test.tag = "skipped"
    tree.write(xml_report_path)
    print("Xml report %s rewritten" % xml_report_path)


if report_path_list:
    for report_path in report_path_list:
        if ".xml" in report_path:
            xml_updater(report_path)
        elif ".json" in report_path:
            json_updater(report_path)

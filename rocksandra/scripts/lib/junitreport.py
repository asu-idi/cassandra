# Copyright (c) 2015-present, Facebook, Inc.
# All rights reserved.
#
# This source code is licensed under the license found in the LICENSE file in
# the root directory of this source tree.

from collections import namedtuple

from xml.etree.ElementTree import ElementTree


TestFailure = namedtuple('TestFailure', ['testname', 'error'])

def _testname(testcase_element):
    return "%s(%s)" % (testcase_element.get('name'), testcase_element.get('classname'))

def failures(test_summary):
    doc = ElementTree(file=test_summary)
    failures = [TestFailure(_testname(t), t.find('./failure').text) for t in doc.findall('.//testcase[failure]')]
    errors = [TestFailure(_testname(t), t.find('./error').text) for t in doc.findall('.//testcase[error]')]
    return failures + errors

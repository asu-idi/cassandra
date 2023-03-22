#!/usr/bin/env python

# Copyright (c) 2015-present, Facebook, Inc.
# All rights reserved.
#
# This source code is licensed under the license found in the LICENSE file in
# the root directory of this source tree.

import json

def generate_legocastle_job(id_num=0, start_idx=0, end_idx=None, steps=None, command_args=None,
        job_args=None, job_capabilities=None):
    """
    Return a dict containing a Sandcastle-based job definition.
    """
    test_cmd = ["./scripts/test",
                "-a",
                "--runners=2",
                "--timeout=900000",
                "--start=%d" % start_idx]
    if end_idx is not None: test_cmd.append("--end=%d" % end_idx)

    name = "uTest{}: {} to {}".format(id_num, start_idx, end_idx if end_idx is not None else "end")
    alias = "utest{}-{}_to_{}".format(id_num, start_idx, end_idx if end_idx is not None else "end")

    return {
        "alias": alias,
        "name": name,
        "command": "SandcastleUniversalCommand",
        "tags": ["ig-cassandra"],
        "priority": 3,
        "args":
        {
            "steps":
            [
                {
                    "name": "run uTest",
                    "shell": " ".join(test_cmd),
                    "parser": "scripts/test_report"
                }
            ]

        },
        "capabilities":
        {
            "tenant": "ig-cassandra",
            "type": "lego",
            "vcs": "ig-cassandra-git"
        }
    }

def generate_legocastle_spec_string(commands):
    """
    Returns a string representation of a list of sandcastle commands,
    skipping None values.
    """
    return json.dumps([command for command in commands if command])

if __name__ == "__main__":
    jobs = [generate_legocastle_job(0, 0, 42)]
    jobs += [generate_legocastle_job(1, 42, 74)]
    jobs += [generate_legocastle_job(2, 74, 89)]
    jobs += [generate_legocastle_job(3, 89, 197)]
    jobs += [generate_legocastle_job(4, 197, 300)]
    jobs += [generate_legocastle_job(5, 300)]

    print(generate_legocastle_spec_string(jobs))

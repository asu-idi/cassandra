<?php
// Copyright 2004-present Facebook. All Rights Reserved.

final class FacebookDiffCreatedListener extends PhutilEventListener {

  public function register() {
    $this->listen(ArcanistEventType::TYPE_DIFF_WASCREATED);
  }

  public function handleEvent(PhutilEvent $event) {
    if ($event->getValue('unitResult') == ArcanistUnitWorkflow::RESULT_SKIP) {
      return;
    }

    $diffID = $event->getValue('diffID');

    $build_artifact = array(
      'name' => 'rpm',
      'paths' => array(
        'build/rpm',
      ),
      'report' => array(
        array(
          'type' => 'phcomment',
        ),
      ),
    );

    $build_step = array(
      'name' => 'Build Cassandra',
      'shell' => './scripts/build',
      'artifacts' => array(
        $build_artifact,
      ),
    );

    $test_step = array(
      'name' => 'Test Cassandra',
      'shell' => 'scripts/determinator.py',
      'determinator' => true,
    );

    $cmd_args = array(
      'name' => 'Instagram Cassandra',
      'oncall' => 'instagram',
      'steps' => array(
        $test_step,
      ),
    );

    $job = array(
      'command' => 'SandcastleUniversalCommand',
      'args' => $cmd_args,
      'capabilities' => array(
        'tenant' => 'ig-cassandra',
        'vcs' => 'ig-cassandra-git',
        'type' => 'lego',
      ),
      'diff' => $diffID,
      'alias' => 'ig-cassandra-test-main',
      'source' => 'arc',
      'priority' => 3
    );

    $sandcastle = new ArcanistSandcastleClient($event->getValue('workflow'));
    $sandcastle->createBundle();
    $sandcastle->enqueue($job);
  }

}

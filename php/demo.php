<?php

  echo "Session started: " . session_start();
  echo "<br/>"
  echo "SessionID: " . session_id();
  echo "<br/>"

  // get App server context:
  $ctx = quercus_get_servlet_context();
  // get attribute from the context, provided it was added with such a name before.
  $app = $ctx->getAttribute("application");

  echo "Call to java: " . $app->myMegaMethod(1);

  return $ret;
?>

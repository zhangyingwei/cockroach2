workflow "New workflow" {
  on = "push"
  resolves = ["clean and deploy"]
}

action "tag-filter" {
  uses = "actions/bin/filter@707718ee26483624de00bd146e073d915139a3d8"
  args = "tag"
}

action "clean and deploy" {
  uses = "LucaFeger/action-maven-cli@aed8a1fd96b459b9a0be4b42a5863843cc70724e"
  needs = ["tag-filter"]
  args = "clean test"
}

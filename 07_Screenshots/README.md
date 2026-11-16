# Screenshots

Evidence of test execution. Save the images in this folder with these names. The README and reports refer to them.

| File | What to capture |
|---|---|
| `01_project_structure.png` | IntelliJ project tree showing `src/main` and `src/test` |
| `02_cycle1_junit_failures.png` | JUnit run on **v1.0**: 77 passed, 10 failed (red bar), with the failing test names visible |
| `03_cycle1_failure_detail_DEF01.png` | Failure detail for TC-BVA-30: `expected: <50> but was: <25>` |
| `04_cycle1_failure_detail_DEF02.png` | Failure detail for TC-DT-08: `expected: <20> but was: <25>` |
| `05_cycle2_junit_all_pass.png` | JUnit run on **v2.0**: 87 passed, green bar |
| `06_parameterized_tests_expanded.png` | One parameterized test (e.g. Boundary Value Analysis) expanded to show all TC IDs |
| `07_jacoco_coverage.png` | JaCoCo report `target/site/jacoco/index.html`, or IntelliJ "Run with Coverage" panel |
| `08_github_commit_history.png` | GitHub commit history showing steady progress |
| `09_pit_mutation_report.png` | _(Optional)_ PIT mutation testing report |

To embed one in a markdown file:
```markdown
![Cycle 2 all tests pass](../07_Screenshots/05_cycle2_junit_all_pass.png)
```

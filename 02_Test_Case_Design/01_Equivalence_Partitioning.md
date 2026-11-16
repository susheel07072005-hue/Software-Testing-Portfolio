# Equivalence Partitioning (EP)

**Idea:** Split each input's domain into classes where the system should behave the same way, then test one representative value from each class. If one value in a class works, the others should too.

Automated in: `05_Test_Automation/src/test/java/com/msrit/travel/EquivalencePartitioningTest.java`

## 1. Number of Travellers (rule: 1 to 10)

| Class | Partition | Valid? | Representative |
|---|---|---|---|
| EC1 | count < 1 | Invalid | -3 |
| EC2 | 1 ≤ count ≤ 10 | Valid | 5 |
| EC3 | count > 10 | Invalid | 15 |

## 2. Email Address (rule: name@domain.tld)

| Class | Partition | Valid? | Representative |
|---|---|---|---|
| EC4 | Well-formed email | Valid | `shreyas@gmail.com` |
| EC5 | Missing `@` | Invalid | `shreyasgmail.com` |
| EC6 | Missing domain extension | Invalid | `shreyas@gmail` |
| EC7 | Empty string | Invalid | `""` |
| EC8 | Null | Invalid | `null` |

## 3. Mobile Number (rule: 10 digits, starts with 6–9)

| Class | Partition | Valid? | Representative |
|---|---|---|---|
| EC9 | 10 digits starting 6–9 | Valid | `9876543210` |
| EC10 | 10 digits starting 0–5 | Invalid | `5876543210` |
| EC11 | Wrong length | Invalid | `98765` |
| EC12 | Contains non-digits | Invalid | `98765abcde` |

## 4. Password (rule: 8–16 chars, at least one letter and one digit)

| Class | Partition | Valid? | Representative |
|---|---|---|---|
| EC13 | Letters + digits, length 8–16 | Valid | `Travel2026` |
| EC14 | Letters only | Invalid | `travelonly` |
| EC15 | Digits only | Invalid | `12345678` |

_Password length boundaries are covered in BVA._

## 5. Package Search (destination text, budget)

Catalogue used: Goa Beach Escape (₹5000), Mysore Heritage Tour (₹3500), Kashmir Valley Trip (₹18000), Kerala Backwaters (₹12000), North Goa Adventure (₹7500).

| Class | Partition | Valid? | Representative | Expected |
|---|---|---|---|---|
| EC16 | Destination exists (exact case) | Valid | `Goa` | 2 packages |
| EC17 | Destination exists (different case) | Valid | `goa` | 2 packages |
| EC18 | Destination does not exist | Valid | `Paris` | Empty list |
| EC19 | Budget < 0 | Invalid | `-1` | Rejected |
| EC20 | Blank destination, budget given | Valid | `"  "`, ₹8000 | 3 packages (≤ ₹8000) |

## Test Cases

| TC ID | Class | Input | Expected Result |
|---|---|---|---|
| TC-EP-01 | EC2 | travellers = 5 | Accepted |
| TC-EP-02 | EC1 | travellers = -3 | Rejected |
| TC-EP-03 | EC3 | travellers = 15 | Rejected |
| TC-EP-04 | EC4 | `shreyas@gmail.com` | Accepted |
| TC-EP-05 | EC5 | `shreyasgmail.com` | Rejected |
| TC-EP-06 | EC6 | `shreyas@gmail` | Rejected |
| TC-EP-07 | EC7 | `""` | Rejected |
| TC-EP-08 | EC8 | `null` | Rejected |
| TC-EP-09 | EC9 | `9876543210` | Accepted |
| TC-EP-10 | EC10 | `5876543210` | Rejected |
| TC-EP-11 | EC11 | `98765` | Rejected |
| TC-EP-12 | EC12 | `98765abcde` | Rejected |
| TC-EP-13 | EC13 | `Travel2026` | Accepted |
| TC-EP-14 | EC14 | `travelonly` | Rejected |
| TC-EP-15 | EC15 | `12345678` | Rejected |
| TC-EP-16 | EC16 | search `Goa` | 2 results |
| TC-EP-17 | EC17 | search `goa` | 2 results |
| TC-EP-18 | EC18 | search `Paris` | 0 results |
| TC-EP-19 | EC19 | budget -1 | `IllegalArgumentException` |
| TC-EP-20 | EC20 | blank destination, budget 8000 | 3 results |

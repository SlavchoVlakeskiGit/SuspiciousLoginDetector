# Suspicious Login Detector (Java Version)

A small Java tool to detect suspicious login activity by counting failed login attempts per user.

## Features
- Reads a CSV login log
- Counts failed logins per user
- Flags users exceeding a threshold (default: 3)
- Supports command-line arguments for input file, threshold, and output file
- Saves flagged users to a CSV

## Folder Structure


SuspiciousLoginDetector/
├─ src/
│ ├─ Main.java # Core detection logic
├─ data/
│ ├─ sample_log.csv # Example login log
├─ README.md


## Usage

1. **Compile the program**

```bash
javac src/Main.java

Run with default CSV and threshold

java -cp src Main

Run with custom input file, threshold, and output file

java -cp src Main data/sample_log.csv 2 data/suspicious_users.csv

data/sample_log.csv → input log file

2 → failed attempt threshold

data/suspicious_users.csv → output file

Sample Log File (data/sample_log.csv)
timestamp,username,status
2026-03-10 08:01,user1,success
2026-03-10 08:05,user2,failure
2026-03-10 08:06,user2,failure
2026-03-10 08:07,user2,failure
2026-03-10 08:10,user3,success
Output

The program creates data/suspicious_users.csv with this structure:

username,failed_attempts
user2,3
Notes

Make sure the CSV files are in the data/ folder

Threshold can be changed via CLI arguments

Works with Java 8+

Robust handling of malformed lines
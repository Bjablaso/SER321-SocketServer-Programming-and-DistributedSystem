# Assignment 2: Upper Layers of the OSI Model

## Overview
This README documents my findings and results for **Assignment 2: Upper Layers of the OSI Model**. The assignment focuses on understanding HTTP requests, exploring the GitHub API, and observing network traffic through tools like Wireshark.

## Prerequisites
Before starting, ensure the following are completed:
1. **Module 2 readings and videos** on Canvas.
2. Run and understand the following examples from the GitHub repository:
   - `Network/SimpleGrabHttpURL`
   - `Network/SimpleGrabURL`
   - `Network/HTTP-JSON`
3. **Setup a second device** (e.g., a second computer or AWS EC2 instance).
4. Review **Wireshark tutorials**.
5. Understand the **lower layers of the OSI model** (Module 1).

## Learning Objectives
- Understand the purpose and function of upper-layer network protocols.
- Differentiate between HTTP and HTTPS, including message traffic differences.
- Gain familiarity with terminal/command-line tools.

---

## Part 1: Understanding HTTP

### 1.1 List Repositories for a User
**API Call:**
```
https://api.github.com/users/Bjablaso/repos
```

**Description:** 
> This call retrieves all public repositories for the specified GitHub user Bjablaso.

**Deliverable:**
- URL: [List Repositories](https://api.github.com/users/Bjablaso/repos)
- **Screenshot:** [Insert your screenshot here]
> ![img.png](UnderstandingHttp/img.png)

---

### 1.2 Get Information About a Specific Repository
**API Call:**
```
https://api.github.com/repos/Bjablaso/Calculator
```

**Description:** 
>This call fetches detailed information about the `Calculator` repository owned by the user `Bjablaso`.

**Deliverable:**
- URL: [Repository Information](https://api.github.com/repos/Bjablaso/Calculator)
- **Screenshot:** [Insert your screenshot here]
> ![img_1.png](UnderstandingHttp/img_1.png)
---

### 1.3 List All Commits in a Repository
**API Call:**
```
https://api.github.com/repos/Bjablaso/Calculator/commits
```

**Description:** 
>This call retrieves all commits on the default branch of the `Bjablaso` repository.

**Deliverable:**
- URL: [List Commits](https://api.github.com/repos/Bjablaso/Calculator/commits)
- **Screenshot:** [Insert your screenshot here]
![img_2.png](UnderstandingHttp/img_2.png)
---

### 1.4 Specify a Branch and Page Limit
**API Call:**
```
https://api.github.com/repos/Bjablaso/Bjablason_SiliconVSim/commits?sha=BattleAdvantage&per_page=40
```

**Description:** 
>This call retrieves up to 40 commits on a specified branch (`BattleAdvantage`) of the `Bjablason_SiliconVSim` repository.

**Deliverable:**
- URL: [Branch Commits](https://api.github.com/repos/Bjablaso/Bjablason_SiliconVSim/commits?sha=BattleAdvantage&per_page=40)
- **Screenshot:** [Insert your screenshot here]
![img.png](UnderstandingHttp/img3.png)
---

### 1.5 Additional API Call with Query Parameters
**API Call:**
```
https://api.github.com/repos/Bjablaso/Bjablason_SiliconVSim/contributors
```

**Description:** 
> This call lists all contributors for the `Bjablaso` repository.

**Deliverable:**
- URL: [List Contributors](https://api.github.com/repos/Bjablaso/Bjablason_SiliconVSim/contributors)
- **Screenshot:** [Insert your screenshot here]
![img.png](UnderstandingHttp/img4.png)
---

## Part 2: Explanation Questions

### 2.1 Explanation of API Calls
- **List Repositories:** Retrieves all public repositories for a user. [Documentation](https://docs.github.com/en/rest/repos/repos#list-repositories-for-a-user)
- **Repository Information:** Fetches details of a specific repository. [Documentation](https://docs.github.com/en/rest/repos/repos#get-a-repository)
- **List Commits:** Lists commits on the default branch of a repository. [Documentation](https://docs.github.com/en/rest/commits/commits#list-commits)
- **Branch Commits with Pagination:** Retrieves commits on a specified branch with a page limit. [Documentation](https://docs.github.com/en/rest/commits/commits#list-commits)
- **List Contributors:** Lists all contributors to a repository. [Documentation](https://docs.github.com/en/rest/repos/repos#list-repository-contributors)

---

### 2.2 Stateless vs. Stateful Communication
- **Stateless Communication:**
  - Each request is independent, with no memory of previous interactions.
  - Example: HTTP. Each API call provides all necessary data (e.g., authentication, query parameters).

- **Stateful Communication:**
  - The server retains information about client interactions to process subsequent requests.
  - Example: Online banking systems that maintain user sessions.

---

## Part 3: Observing Traffic with Wireshark
**Steps Taken:**
1. Opened Wireshark and started capturing network traffic.
2. Performed API calls in the browser while capturing HTTP traffic.
3. Filtered traffic using `http` to isolate relevant packets.
4. Observed HTTP GET request headers and response details.

**Findings:**
- HTTP traffic consists of clear-text messages, including headers and payload.
- Each API call corresponds to a new HTTP GET request, reflecting stateless behavior.
> ![img.png](UnderstandingHttp/img5.png)
> ![img.png](UnderstandingHttp/img6.png)



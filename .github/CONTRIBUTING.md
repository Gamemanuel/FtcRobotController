
---


# Contributing Guide – FTC Robot Controller (Team 10523)

We welcome contributions from team members and collaborators.  
This guide explains how to contribute code, report issues, and create official releases.

---

## Development Workflow

1. **Fork & Clone**
   - Fork the repository to your GitHub account.
   - Clone your fork locally:
     ```bash
     git clone https://github.com/Gamemanuel/FtcRobotController.git
     ```
     ```bash
     cd FtcRobotController
     ```

2. **Create a Branch**
   - Use descriptive branch names:
     ```bash
     git checkout -b feature/add-color-sensor-support
     ```

3. **Code Style**
   - Follow FTC SDK Java conventions.
   - Keep OpMode names descriptive and consistent.
   - (No Using Peoples names as the name of the function (nicksDrive)
   - Document hardware mappings in comments.

4. **Testing**
   - Test on a physical Robot Controller before submitting.
   - Ensure no build errors in Android Studio.

5. **Commit & Push**
   - Write clear commit messages:
     ```
     feat: add support for AndyMark ToF sensor
     fix: correct AprilTag filtering for Obelisk tags
     ```


Issue Reporting
- Use GitHub Issues for bugs, feature requests, or questions.
- Include:
- FTC SDK version
- Steps to reproduce
- Expected vs. actual behavior
- Screenshots/logs if applicable


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

6. **Pull Request**
   - Open a PR to the `main` branch.
   - Describe your changes and link related issues.

---

## Creating a Release

When preparing a new release (example: **Version 11.0 – 20250827-105138**):

1. **Update `README.md`**
   - Ensure installation and usage instructions are current.

2. **Update `CHANGELOG.md`**
   - Add a new section for the release:
     ```markdown
     ## Version 11.0 (20250827-105138)
     ### Enhancements
      ### bugs Fixed
     ```

3. **Tag the Release**
   ```bash
   git tag -a v11.0 -m "Version 11.0 (20250827-105138)"
   git push origin v11.0

   - Publish on GitHub
- Go to Releases → Draft a new release.
- Select the tag, add the changelog, and publish.

Issue Reporting
- Use GitHub Issues for bugs, feature requests, or questions.
- Include:
- FTC SDK version
- Steps to reproduce
- Expected vs. actual behavior
- Screenshots/logs if applicable

🤝 Code of Conduct
Be respectful, collaborative, and constructive.
We are here to learn, compete, and have fun.

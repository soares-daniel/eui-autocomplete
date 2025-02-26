<div align="center">
  <img width="140" src="https://eui.ecdevops.eu/eui-showcase-ux-components-18.x/assets/images/common/eui-logo.svg" alt="Icon"/>
  <h2 align="center">EUI Autocomplete</h2>
  <p align="center">  Simple <a href="https://eui.ecdevops.eu/">EUI</a> autocomplete plugin for IntelliJ IDEA.</p>
</div>
<div align="center">

![Build](https://github.com/soares-daniel/eui-autocomplete/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/26235-eui-autocomplete.svg)](https://plugins.jetbrains.com/plugin/26235-eui-autocomplete)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/26235-eui-autocomplete.svg)](https://plugins.jetbrains.com/plugin/26235-eui-autocomplete)

</div>

<!-- Plugin description -->

## Description

This plugin provides autocomplete for EUI HTML tags in IntelliJ IDEA.

## Features

- Autocomplete for EUI HTML tags
- Autocomplete for EUI properties
- Documentation for EUI HTML tags and properties (coming soon)

## Usage

The plugin will automatically look for your node_modules folder and create the autocompletion as well as recognize (coming soon) your components independent of your version.
When you perform an update of your EUI version, for now you can reload the plugin functionality by clicking on the `Reload EUI Autocomplete` button in the `Tools` menu.

<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "eui-autocomplete"</kbd> >
  <kbd>Install</kbd>
  
- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/26235-eui-autocomplete) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/26235-eui-autocomplete/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/soares-daniel/eui-autocomplete/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation

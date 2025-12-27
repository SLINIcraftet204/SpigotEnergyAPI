# SpigotEnergyAPI

SpigotEnergyAPI is a **modular infrastructure API** for **Paper / Spigot / Bukkit** servers.
It is designed as a **shared foundation** that other plugins (called *modules*) can build upon
to create interconnected energy, steam, or artifact-based systems.

This project intentionally focuses on **infrastructure, not gameplay**.

---

## What is SpigotEnergyAPI?

SpigotEnergyAPI provides a common technical layer for plugins that want to work with:

- custom block-based networks
- energy or steam transfer
- pipes, machines, generators, and storage blocks
- cross-plugin interoperability

The API itself does **not** add machines, pipes, items, or recipes.
Instead, it allows independent plugins to seamlessly connect their systems together.

Example use cases:
- Steam pipes from Plugin A powering a machine from Plugin B
- Generators from one module feeding consumers from another
- Artifact or steampunk-style systems built across multiple plugins

---

## Core Design Goals

- **Modular by design**  
  Every feature is provided by external modules, not hardcoded into the API.

- **Cross-plugin compatibility**  
  Plugins automatically connect as long as they use the same network medium.

- **Persistent block data**  
  All network-related data is stored directly on blocks and survives restarts.

- **Automatic networking**  
  The API handles pathfinding, routing, and transfer logic.

- **Low coupling**  
  Modules never need to know about each other.

---

## What the API Provides

- Module registration system
- Block type registry (pipes, producers, consumers, storage)
- Custom block typing via persistent metadata
- Network engines (currently Steam)
- Built-in pathfinding (BFS-based)
- Automatic transfer ticks
- Plugin-safe cleanup on block removal
- Debug utilities for block data

---

## What the API Does NOT Provide

This is intentional.

- No items or crafting recipes
- No GUIs
- No textures or resource packs
- No balancing or progression logic
- No predefined machines or pipes

All of the above are the responsibility of modules.

---

## How Modules Integrate

1. The API is installed as a plugin on the server.
2. Modules declare a dependency on `SpigotEnergyAPI` in their `plugin.yml`.
3. Modules register themselves and their block types at runtime.
4. The API automatically connects compatible blocks across all modules.

---

## Installation

1. Download the SpigotEnergyAPI jar from the **Releases** section
   or build it yourself from source.
2. Place the jar into the server's `plugins` directory.
3. Restart the server.
4. Install one or more compatible modules.

---

## Documentation

Detailed documentation for developers is available in the **Wiki**:

- Getting Started (Developer Entry)
- Module Development Guide
- Network & Steam API
- Examples & Best Practices

The Wiki is designed so that module developers do **not** need to read the API source code.

---

## Target Audience

SpigotEnergyAPI is intended for:
- plugin developers
- technical server owners
- mod-like system designers
- steampunk / industrial / artifact-themed projects

It is **not** intended as a ready-to-play gameplay plugin.

---

## Status & Scope

This project is under active development.
APIs are designed to be extensible, and additional network media
(e.g. Flux, Energy, Fluids) can be added in the future.

Backward compatibility is considered, but breaking changes may occur
during early development stages.

---

## License

Copyright SLINIcraftet204, TamashiiMon and CrayonSMP 2025
All Rights reserved

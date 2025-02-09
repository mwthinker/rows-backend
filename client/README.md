# Client

## Installation
```bash
corepack enable
yarn install
```

## Commands
```bash
yarn dev
```
### Potential errors
*On Windows:* Might require repo being stored on C:-drive, due to bug in Vite. 


## VSCode
For language support in VSCode run 
```bash
yarn dlx @yarnpkg/sdks vscode
```
Further, put the following in .vscode/settings.json
```json
{
  "search.exclude": {
    "**/.yarn": true,
    "**/.pnp.*": true
  },
  "eslint.nodePath": "./client/.yarn/sdks",
  "typescript.tsdk": "./client/.yarn/sdks/typescript/lib",
  "typescript.enablePromptUseWorkspaceTsdk": true
}
```
The above will enable us to open the whole project in VSCode.

More info [here](https://yarnpkg.com/getting-started/editor-sdks#vscode) 

CSS files must be set to language Tailwind CSS in order to get IntelliSense.


```bash
# Dummy server (probably broken)
node dummy-server/server.js
```




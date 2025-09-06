import {themes as prismThemes} from 'prism-react-renderer';
import type {Config} from '@docusaurus/types';
import type * as Preset from '@docusaurus/preset-classic';

import LatestVersion from '@types/commonTypes';

const algoliaConfig = require('./algolia.config.json');
const googleAnalyticsConfig = require('./google-analytics.config.json');

// const lightCodeTheme = require('prism-react-renderer/themes/github');
// const darkCodeTheme = require('prism-react-renderer/themes/dracula');
const lightCodeTheme = prismThemes.nightOwlLight;
const darkCodeTheme = prismThemes.nightOwl;


const isEmptyObject = (obj: object) => Object.keys(obj).length === 0;

const isSearchable = !isEmptyObject(algoliaConfig);
const hasGoogleAnalytics = !isEmptyObject(googleAnalyticsConfig);

import LatestVersionImported from './latestVersion.json';
const latestVersionFound = LatestVersionImported as LatestVersion;


const config: Config = {
  title: 'hedgehog-extra',
  tagline: 'Extra tools for Hedgehog for Scala',
  favicon: 'img/favicon.png',

  // Set the production url of your site here
  url: 'https://hedgehog-extra.kevinly.dev',
  // Set the /<baseUrl>/ pathname under which your site is served
  // For GitHub pages deployment, it is often '/<projectName>/'
  baseUrl: '/',

  // GitHub pages deployment config.
  // If you aren't using GitHub pages, you don't need these.
  organizationName: 'kevin-lee', // Usually your GitHub org/user name.
  projectName: 'scala-hedgehog-extra', // Usually your repo name.

  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',

  // Even if you don't use internationalization, you can use this field to set
  // useful metadata like html lang. For example, if your site is Chinese, you
  // may want to replace "en" with "zh-Hans".
  i18n: {
    defaultLocale: 'en',
    locales: ['en'],
  },

  presets: [
    [
      'classic',
      {
        docs: {
          sidebarPath: './sidebars.ts',
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
        },
        docs: {
          path: '../generated-docs/docs/',
          sidebarPath: require.resolve('./sidebars.ts'),
          "lastVersion": "current",
          "versions": {
            "current": {
              "label": `v${latestVersionFound.version}`,
            },
          }
        },
        theme: {
          customCss: './src/css/custom.css',
        },
      } satisfies Preset.Options,
    ],
  ],

  themeConfig: {
    // Replace with your project's social card
    image: 'img/hedgehog-extra-social-card.png',
    docs: {
      sidebar: {
        hideable: true,
      },
    },
    navbar: {
      title: 'hedgehog-extra',
      logo: {
        alt: 'hedgehog-extra Logo',
        src: 'img/logo.svg',
      },
      items: [
        {
          type: 'docSidebar',
          sidebarId: 'docsSidebar',
          position: 'left',
          label: 'Docs',
        },
        {
          type: 'docsVersionDropdown',
          position: 'right',
          dropdownActiveClassDisabled: true,
          dropdownItemsAfter: [
            {
              to: '/versions',
              label: 'All versions',
            },
          ],
        },
        {
          href: 'https://github.com/kevin-lee/scala-hedgehog-extra',
          label: 'GitHub',
          position: 'right',
        },
      ],
    },
    footer: {
      style: 'dark',
      links: [
        {
          title: 'Docs',
          items: [
            {
              label: 'Docs',
              to: '/docs',
            },
          ],
        },
        {
          title: 'More',
          items: [
            {
              label: 'GitHub',
              href: 'https://github.com/kevin-lee/scala-hedgehog-extra',
            },
          ],
        },
      ],
      copyright: `Copyright © ${new Date().getFullYear()} hedgehog-extra. Built with Docusaurus.<br>
      <a href="https://www.flaticon.com/free-icons/hedgehog" title="hedgehog icons">Hedgehog</a>, <a href="https://www.flaticon.com/free-icons/toolbox" title="toolbox icons">Toolbox</a> and <a href="https://www.flaticon.com/free-icons/puzzle" title="puzzle icons">Puzzle</a> icons</a> created by Freepik - Flaticon
      `,
    },
    prism: {
      theme: lightCodeTheme,
      darkTheme: darkCodeTheme,
      additionalLanguages: [
        'bash',
        'diff',
        'json',
        'java',
        'scala',
      ],
    },
  } satisfies Preset.ThemeConfig,
};

if (isSearchable) {
  config['themeConfig']['algolia'] = algoliaConfig;
}

export default config;

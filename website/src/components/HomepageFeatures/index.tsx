import clsx from 'clsx';
import Heading from '@theme/Heading';
import styles from './styles.module.css';

type FeatureItem = {
  title: string;
  Svg: React.ComponentType<React.ComponentProps<'svg'>>;
  description: JSX.Element;
};

const FeatureList: FeatureItem[] = [
  {
    Png: require('@site/static/img/hedgehog.png').default,
    title: 'For Hedgehog',
    description: (
      <>
        Hedgehog Extra is for Hedgehog users.
      </>
    ),
  },
  {
    Png: require('@site/static/img/tool-box.png').default,
    title: 'Extra Tools',
    description: (
      <>
        It provide extra convenience for Hedgehog users.
      </>
    ),
  },
  {
    Png: require('@site/static/img/puzzle.png').default,
    title: 'Save Time',
    description: (
      <>
        So you can save time with pre-defined value <code>Gen</code>s and type <code>Gen</code>s.
      </>
    ),
  },
];

function FeatureSvg({title, Svg, description}: FeatureItem) {
  return (
    <div className={clsx('col col--4')}>
      <div className="text--center">
        <Svg className={styles.featureSvg} role="img" />
      </div>
      <div className="text--center padding-horiz--md">
        <Heading as="h3">{title}</Heading>
        <p>{description}</p>
      </div>
    </div>
  );
}


function FeaturePng({Png, title, description}) {
  return (
      <div className={clsx('col col--4')}>
        <div className="text--center">
          <img src={Png} className={styles.featurePng} alt={title} />
        </div>
        <div className="text--center padding-horiz--md">
          <h3>{title}</h3>
          <p>{description}</p>
        </div>
      </div>
  );
}

export default function HomepageFeatures(): JSX.Element {
  return (
      <section className={styles.features}>
        <div className="container">
          <div className="row">
            {FeatureList.map((props, idx) => (
                props.hasOwnProperty('Svg') ?
                    <FeatureSvg key={idx} {...props} /> :
                    <FeaturePng key={idx} {...props} />
            ))}
          </div>
        </div>
      </section>
  );
}


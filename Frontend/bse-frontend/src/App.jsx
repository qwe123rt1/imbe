export default function App() {
  return (
    <main className="app-shell">
      <style>{styles}</style>

      <section className="intro">
        <p className="eyebrow">BSE Frontend</p>
        <h1>Frontend shell only</h1>
        <p>
          This screen is intentionally disconnected. Keep the UI simple until one backend contract is final.
        </p>
      </section>

      <section className="panel">
        <h2>Current status</h2>
        <div className="status-grid">
          <div className="status-item">
            <strong>Pages</strong>
            <span>No extra pages connected.</span>
          </div>
          <div className="status-item">
            <strong>Backend</strong>
            <span>No backend calls connected.</span>
          </div>
          <div className="status-item">
            <strong>Next step</strong>
            <span>Choose one backend contract before adding any connection.</span>
          </div>
        </div>
      </section>
    </main>
  );
}

const styles = `
  :root {
    color: #172026;
    background: #f5f7f8;
    font-family: Arial, Helvetica, sans-serif;
  }

  * {
    box-sizing: border-box;
  }

  body {
    margin: 0;
    min-width: 320px;
  }

  .app-shell {
    width: min(960px, calc(100% - 32px));
    margin: 0 auto;
    padding: 32px 0;
  }

  .intro {
    margin-bottom: 24px;
  }

  .eyebrow {
    margin: 0 0 8px;
    color: #0f766e;
    font-size: 13px;
    font-weight: 700;
    text-transform: uppercase;
  }

  h1,
  h2,
  p {
    margin-top: 0;
  }

  h1 {
    margin-bottom: 12px;
    font-size: 34px;
  }

  h2 {
    margin-bottom: 12px;
    font-size: 20px;
  }

  p {
    line-height: 1.6;
  }

  .panel {
    margin-bottom: 18px;
    padding: 20px;
    border: 1px solid #d9e2e5;
    border-radius: 8px;
    background: #ffffff;
  }

  .status-grid {
    display: grid;
    grid-template-columns: repeat(3, minmax(0, 1fr));
    gap: 12px;
  }

  .status-item {
    min-height: 112px;
    border: 1px solid #d9e2e5;
    border-radius: 8px;
    padding: 14px;
    background: #f8fbfb;
  }

  .status-item strong,
  .status-item span {
    display: block;
  }

  .status-item strong {
    margin-bottom: 8px;
  }

  .status-item span {
    color: #526168;
    line-height: 1.5;
  }

  @media (max-width: 720px) {
    h1 {
      font-size: 28px;
    }

    .status-grid {
      grid-template-columns: 1fr;
    }
  }
`;
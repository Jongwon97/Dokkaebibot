import React from 'react';
import { ProgressSpinner } from 'primereact/progressspinner';

export default function Loading() {
    return (
        <div style={{
            width: '100vw', height: '100vh',
            position: 'fixed', top: '0px', left: '0px',
            opacity: '40%', backgroundColor: "white", zIndex: 1,
            cursor: 'wait'
        }}>
            <div className="card">
                <ProgressSpinner style={{
                    width: '50px', height: '50px',
                    position: 'absolute', top: '50vh', left: '50vw'
                }} strokeWidth="6" fill="var(--surface-ground)" animationDuration=".5s" />
            </div>
        </div>
    );
}
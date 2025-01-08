import React from 'react';
import { ErrorBoundary } from "react-error-boundary";
import './App.css';
import ErrorFallback from "./pages/Error/ErrorFallback";
import { Route, Routes } from "react-router-dom";
import ChunkFile from './pages/ChunkFile';

const App: React.FC = () => {
  return (
    <>
      <ErrorBoundary FallbackComponent={ErrorFallback}>
        <Routes>
          <Route path="/" element={<ChunkFile />} />
          <Route path="*" element={<div>Not Found</div>} />
        </Routes>
      </ErrorBoundary>
    </>
  );
}

export default App;

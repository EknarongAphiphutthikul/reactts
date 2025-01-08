import React, { FunctionComponent, useEffect } from "react";
import { FallbackProps } from "react-error-boundary";
import ErrorImage from "../../../assets/images/error.png";

const ErrorFallback: FunctionComponent<FallbackProps> = ({ error, resetErrorBoundary }) => {
  const message = error?.message;
  useEffect(() => {
    return () => {
      resetErrorBoundary();
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div>
      <h1>Something went wrong!</h1>
      <pre>{message}</pre>
      <img src={ErrorImage} alt='logo'/>
    </div>
  );
};

export default ErrorFallback;

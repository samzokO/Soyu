import { useState } from 'react';

function useAsync(asyncFunction) {
  const [pending, setPending] = useState(false);
  const [error, setError] = useState(null);
  const Wrapped = async (...args) => {
    try {
      setError(null);
      setPending(true);
      return await asyncFunction(...args);
    } catch (e) {
      setError(e);
      return e;
    } finally {
      setPending(false);
    }
  };
  return [pending, error, Wrapped];
}
export default useAsync;

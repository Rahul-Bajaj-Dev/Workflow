import { useState, useEffect } from 'react';

                                                                

const useSessionSync = () => {
  const [sessionData, setSessionData] = useState(null);

  useEffect(() => {
                                                

    const handleStorageChange = (e) => {
                                                               

    };

    window.addEventListener('storage', handleStorageChange);
    return () => window.removeEventListener('storage', handleStorageChange);
  }, []);

                                            

  const updateSession = (data) => {
                                                     

    setSessionData(data);
  };

  return { sessionData, updateSession };
};

export default useSessionSync;
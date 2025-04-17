import React, { createContext, useContext } from 'react';
import useSessionSync from '../hooks/useSessionSync';

const SessionContext = createContext();

                                                       

export const SessionProvider = ({ children }) => {
  const { sessionData, updateSession } = useSessionSync();

                                        

  const value = {
    sessionData,
    updateSession,
                                            

  };

  return (
    <SessionContext.Provider value={value}>
      {children}
    </SessionContext.Provider>
  );
};

export const useSession = () => {
  const context = useContext(SessionContext);
  if (!context) {
    throw new Error('useSession must be used within SessionProvider');
  }
  return context;
};

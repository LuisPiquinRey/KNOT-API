import { Children, createContext } from "react"

const AuthContext = createContext();
export function AuthProvider({ children }) {
    const[user, setUser] = useState({
        token: '',
        role: '',
        dataUser: {
            username: '',
            password: ''
        }
    });
    const login = (token, role, dataUser) => {
        setUser({
            token,
            role,
            dataUser
        });
        localStorage.setItem('token', token);
    };
    const logout = () => {
        setUser({
            token: '',
            role: '',
            dataUser: {
                username: '',
                password: ''
            }
        });
        localStorage.removeItem('token');
    };
    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {Children}
        </AuthContext.Provider>
    );
}
export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
}
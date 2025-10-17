document.addEventListener('DOMContentLoaded', () => {
    // --- LÓGICA DA ANIMAÇÃO DO PAINEL ---
    const signUpButton = document.getElementById('signUp');
    const signInButton = document.getElementById('signIn');
    const container = document.getElementById('container');

    if (signUpButton && signInButton && container) {
        signUpButton.addEventListener('click', () => {
            container.classList.add("right-panel-active");
        });

        signInButton.addEventListener('click', () => {
            container.classList.remove("right-panel-active");
        });
    }

    // --- LÓGICA DE CADASTRO (SIGN UP) ---
    const signUpForm = document.getElementById('signUpForm');
    if (signUpForm) {
        const usernameInput = document.getElementById('username');
        const emailInput = document.getElementById('email');
        const cpfInput = document.getElementById('cpf');
        const passwordInput = document.getElementById('password');
        const confirmPasswordInput = document.getElementById('confirmPassword');
        const signUpMessageElement = document.getElementById('signUpMessage');

        // Seleciona os spans para as mensagens de validação
        const usernameValidation = document.getElementById('username-validation');
        const emailValidation = document.getElementById('email-validation');
        const cpfValidation = document.getElementById('cpf-validation');
        const passwordValidation = document.getElementById('password-validation');
        const confirmPasswordValidation = document.getElementById('confirmPassword-validation');

        // Função genérica para atualizar o feedback de um campo
        const updateFeedback = (input, messageElement, isValid, message) => {
            input.classList.toggle('valid', isValid);
            input.classList.toggle('invalid', !isValid);
            messageElement.textContent = message;
            messageElement.style.color = isValid ? '#55FF55' : '#FFAAAA';
        };

        // VALIDAÇÃO EM TEMPO REAL
        usernameInput.addEventListener('input', () => {
            const isValid = usernameInput.value.length >= 3;
            updateFeedback(usernameInput, usernameValidation, isValid, isValid ? '' : 'O nome deve ter pelo menos 3 caracteres.');
        });

        emailInput.addEventListener('input', () => {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            const isValid = emailRegex.test(emailInput.value);
            updateFeedback(emailInput, emailValidation, isValid, isValid ? '' : 'Por favor, insira um e-mail válido.');
        });

        cpfInput.addEventListener('input', () => {
            const cpf = cpfInput.value.replace(/\D/g, '');
            const isValid = cpf.length === 11;
            updateFeedback(cpfInput, cpfValidation, isValid, isValid ? '' : 'O CPF deve conter 11 dígitos.');
        });

        passwordInput.addEventListener('input', () => {
            const isValid = passwordInput.value.length >= 8;
            updateFeedback(passwordInput, passwordValidation, isValid, isValid ? '' : 'A senha deve ter pelo menos 8 caracteres.');
            validateConfirmPassword();
        });

        const validateConfirmPassword = () => {
            const passwordsMatch = passwordInput.value === confirmPasswordInput.value && confirmPasswordInput.value.length > 0;
            updateFeedback(confirmPasswordInput, confirmPasswordValidation, passwordsMatch, passwordsMatch ? 'As senhas conferem!' : 'As senhas não conferem.');
        };

        confirmPasswordInput.addEventListener('input', validateConfirmPassword);

        // SUBMISSÃO DO FORMULÁRIO DE CADASTRO
        signUpForm.addEventListener('submit', async (event) => {
            event.preventDefault();

            const isFormValid = !document.querySelector('#signUpForm input.invalid') && document.querySelector('#signUpForm input.valid');
            if (!isFormValid) {
                signUpMessageElement.textContent = 'Por favor, corrija os campos inválidos.';
                signUpMessageElement.className = 'message error show';
                return;
            }

            const data = {
                username: usernameInput.value,
                email: emailInput.value,
                cpf: cpfInput.value,
                password: passwordInput.value,
                confirmPassword: confirmPasswordInput.value
            };

            try {
                const response = await fetch('/api/clients/register', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });

                const result = await response.json();

                if (response.ok) {
                    signUpMessageElement.textContent = "Cadastro realizado com sucesso! Redirecionando...";
                    signUpMessageElement.className = 'message success show';
                    setTimeout(() => {
                        window.location.href = result.redirectUrl;
                    }, 2000);
                } else {
                    signUpMessageElement.textContent = result.error || 'Erro ao cadastrar.';
                    signUpMessageElement.className = 'message error show';
                }
            } catch (error) {
                console.error('Erro na requisição de cadastro:', error);
                signUpMessageElement.textContent = 'Ocorreu um erro na comunicação. Tente novamente.';
                signUpMessageElement.className = 'message error show';
            }
        });
    }

    // --- LÓGICA DE AUTENTICAÇÃO (SIGN IN) ---
    const signInForm = document.getElementById('signInForm');
    const signInMessageElement = document.getElementById('signInMessage');

    if (signInForm) {
        signInForm.addEventListener('submit', async (event) => {
            event.preventDefault();

            const identifier = document.getElementById('signInCpf').value;
            const password = document.getElementById('signInPassword').value;

            if (!identifier || !password) {
                signInMessageElement.textContent = 'Por favor, preencha o CPF e a senha.';
                signInMessageElement.className = 'message error show';
                return;
            }

            const data = {
                cpf: identifier, // O backend usa o campo 'email' para o CPF no login
                password: password
            };

            try {
                const response = await fetch('/auth/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });

                const result = await response.json();

                if (response.ok) {
                    signInMessageElement.textContent = "Login bem-sucedido! Redirecionando...";
                    signInMessageElement.className = 'message success show';
                    setTimeout(() => {
                        window.location.href = result.redirectUrl; // Redireciona para /home
                    }, 1500);
                } else {
                    signInMessageElement.textContent = result.error || 'Falha na autenticação.';
                    signInMessageElement.className = 'message error show';
                }
            } catch (error) {
                console.error('Erro na requisição de login:', error);
                signInMessageElement.textContent = 'Ocorreu um erro na comunicação. Tente novamente.';
                signInMessageElement.className = 'message error show';
            }
        });
    }
});
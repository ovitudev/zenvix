document.addEventListener('DOMContentLoaded', () => {
    // --- LÓGICA DA ANIMAÇÃO DO PAINEL ---
    const signUpButton = document.getElementById('signUp');
    const signInButton = document.getElementById('signIn');
    const container = document.getElementById('container');

    signUpButton.addEventListener('click', () => {
        container.classList.add("right-panel-active");
    });

    signInButton.addEventListener('click', () => {
        container.classList.remove("right-panel-active");
    });

    // --- LÓGICA DE VALIDAÇÃO E SUBMISSÃO DO CADASTRO (SIGN UP) ---

    // Seleciona os inputs do formulário
    const usernameInput = document.getElementById('username');
    const emailInput = document.getElementById('email');
    const cpfInput = document.getElementById('cpf');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');

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

    // --- VALIDAÇÃO EM TEMPO REAL ---

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

    // --- SUBMISSÃO DO FORMULÁRIO DE CADASTRO ---
    const signUpForm = document.getElementById('signUpForm');
    const messageElement = document.getElementById('signUpMessage');

    signUpForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const isFormValid = !document.querySelector('#signUpForm input.invalid') && document.querySelector('#signUpForm input.valid');
        if (!isFormValid) {
            messageElement.textContent = 'Por favor, corrija os campos inválidos.';
            messageElement.className = 'message error show';
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
            // O endpoint do backend não foi alterado
            const response = await fetch('/api/clients/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });

            // O seu endpoint de API retorna um objeto JSON com redirectUrl
            const result = await response.json();

            if (response.ok) {
                messageElement.textContent = "Cadastro realizado com sucesso! Redirecionando...";
                messageElement.className = 'message success show';
                setTimeout(() => {
                    window.location.href = result.redirectUrl; // Usa a URL de redirecionamento do backend
                }, 2000);
            } else {
                messageElement.textContent = result.error || 'Erro ao cadastrar.'; // Adapte conforme o retorno de erro do seu backend
                messageElement.className = 'message error show';
            }
        } catch (error) {
            console.error('Erro na requisição:', error);
            messageElement.textContent = 'Ocorreu um erro na comunicação. Tente novamente.';
            messageElement.className = 'message error show';
        }
    });
});